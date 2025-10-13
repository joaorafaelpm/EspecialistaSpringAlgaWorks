package com.algaworks.algafood_api.infrastructure.service.query;

import com.algaworks.algafood_api.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood_api.domain.model.Pedido;
import com.algaworks.algafood_api.domain.model.dto.VendaDiaria;
import com.algaworks.algafood_api.domain.model.enuns.StatusPedido;
import com.algaworks.algafood_api.domain.service.VendaQueryService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.Predicate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class VendaQueryServiceImpl implements VendaQueryService {

    @PersistenceContext
    private EntityManager manager;

//    a ideia é, a nossa venda diaria recebe 3 parâmetros, dataCriacao , total de vendas e valor/fatura total
//    Para escrever isso em SQL nós podemos fazer da seguinte forma:

//    select date(p.data_criacao) as data_criacao ,
//      count(p.id) as total_vendas,
//      sum(p.valor_total) as total_faturado
//    from pedido p
//    group by date(p.data_criacao)

//    Agora basta transformar isso em código no criteria:
    @Override
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filter , String timeOffSet) {
        var builder = manager.getCriteriaBuilder();
        var query = builder.createQuery(VendaDiaria.class);
//        from pedido
        var root = query.from(Pedido.class);
        var predicates = new ArrayList<Predicate>();

//        Aqui a gente ta especificando que nós queremos a coluna dataCriacao no tipo date e transformando em um LocalDate, e não em um utc comum
//        isso representa o "date(p.data_criacao)", porém neste exemplo nós não estamos considerando o fuso horário de Brasília, então vamos transformar "date(p.data_criacao)" em "date(convert_tz(p.data_criacao , '+0:00' , '-3:00'))"
        var functionConvertTzDataCriacao = builder.function(
        "convert_tz" , Date.class ,root.get("dataCriacao") ,
                builder.literal("+00:00") , builder.literal(timeOffSet))   ;

        var functionDateDataCriacao = builder.function(
                "date" , Date.class , functionConvertTzDataCriacao );

//        E aqui por fim nós terminamos de construir a estrutura final que recebe a função passando para LocalDate, um count na quantidade de id de produtos daquele dia 'count(id)' e depois 'group by data_criacao' e por fim, a soma nas vendas daquele dia
//        Detalhe importante, isso só funciona por que nós conseguimos criar a classe com isso, já que nós passamos o @AllArgsConstruct no VendaDiaria.class e agora ele pode receber esse construtor
        var selection = builder.construct(VendaDiaria.class ,
                functionDateDataCriacao ,
                builder.count(root.get("id")) ,
                builder.sum(root.get("valorTotal"))
        );

        if (filter.getRestauranteId() != null) {
            predicates.add(builder.equal(root.get("restaurante").get("id") , filter.getRestauranteId()));
        }

        if (filter.getDataCriacaoInicio() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao") ,
                    filter.getDataCriacaoInicio()));
        }
        if (filter.getDataCriacaoFim() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao") ,
                    filter.getDataCriacaoFim()));
        }

//        Evitar pedidos Cancelados ou Criados
        predicates.add(root.get("statusPedido").
                in(StatusPedido.ENTREGUE , StatusPedido.CONFIRMADO));

        query.select(selection);

        query.where(predicates.toArray(new Predicate[0]));

//        Aqui nós fazemos o group by
        query.groupBy(functionDateDataCriacao);

//        Por via de curiosidade, vendo o query de SQL que o hibernate gerou, é exatamente o que nós passamos :
//        Hibernate: select date(p1_0.data_criacao),count(p1_0.id),sum(p1_0.valor_total) from pedido p1_0 group by 1
        return manager.createQuery(query).getResultList();
    }
}
