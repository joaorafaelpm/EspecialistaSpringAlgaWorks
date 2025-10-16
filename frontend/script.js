function consultar() {
  $.ajax({
    url: "http://localhost:8080/formas-pagamento",
    type: "get",

    success: function (response) {
      preencherTabela(response);
    },
  });
}

function cadastrar() {
  let formaPagamentoJson = JSON.stringify({
    descricao: $("#campo-descricao").val(),
  });

  console.log(formaPagamentoJson);

  $.ajax({
    url: "http://localhost:8080/formas-pagamento",
    type: "post",
    data: formaPagamentoJson,
    contentType: "application/json",

    success: function (response) {
      alert("Forma de pagamento adicionada!");
      consultar();
    },

    error: function (error) {
      if (error.status == 400) {
        let problem = JSON.parse(error.responseText);
        alert(problem.userMessage);
      } else {
        alert("Erro ao cadastrar forma de pagamento!");
      }
    },
  });
}

function excluir(formaPagamento) {
  let id = formaPagamento.id;
  $.ajax({
    url: `http://localhost:8080/formas-pagamento/${id}`,
    type: "delete",
    contentType: "application/json",

    success: function (response) {
      alert(`Forma de pagamento de id: '${id}' excluida com sucesso!`);
      consultar();
    },

    error: function (error) {
      if (error.status >= 400 && error.status <= 499) {
        let problem = JSON.parse(error.responseText);
        alert(problem.userMessage);
      } else {
        alert("Erro ao remover forma de pagamento!");
      }
    },
  });
}

function preencherTabela(formasPagamento) {
  $("#tabela tbody tr").remove();

  $.each(formasPagamento, function (i, formaPagamento) {
    let linha = $("<tr>");

    let linkAcao = $("<a href='#'>")
      .text("Excluir")
      .click(function (event) {
        event.preventDefault();
        excluir(formaPagamento);
      });

    linha.append(
      $("<td>").text(formaPagamento.id),
      $("<td>").text(formaPagamento.descricao),
      $("<td>").append(linkAcao)
    );

    linha.appendTo("#tabela");
  });
}

$("#btn-consultar").click(consultar);
$("#btn-cadastrar").click(cadastrar);
