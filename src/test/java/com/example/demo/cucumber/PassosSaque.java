package com.example.demo.cucumber;

import com.example.demo.web.rest.dto.SaqueDTO;
import cucumber.api.java8.En;
import gherkin.deps.com.google.gson.Gson;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


public class PassosSaque {

    SaqueDTO saqueDTO = new SaqueDTO();

    @Autowired
    PassosDeposito passosDeposito;

    MockMvc mockMvc;

    MvcResult mvcResult;

    @Autowired
    private WebApplicationContext context;

    String content;

    @PostConstruct
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Dado("que seja solicitado um saque de {string}")
    public void queSejaSolicitadoUmSaqueDe (String valorDoSaque) {
        this.saqueDTO.setNumeroDaConta(passosDeposito.conta.getId());
        this.saqueDTO.setValorDoSaque(Double.parseDouble(valorDoSaque));
    }

    @Quando("for executada a operação de saque")
    public void forExecutadaAOperacaoDeSaque( ) throws Exception {
        MvcResult result = mockMvc.perform(post("http://localhost:8080/conta/saque")
                .content(new Gson().toJson(this.saqueDTO))
                .contentType(APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print()).andReturn();

        this.mvcResult = result;
        this.content = result.getResponse().getContentAsString();
    }
}
