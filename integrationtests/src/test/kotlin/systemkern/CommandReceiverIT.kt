package systemkern

import org.hamcrest.Matchers.containsString
import org.junit.Test
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post
import org.springframework.restdocs.payload.JsonFieldType.ARRAY
import org.springframework.restdocs.payload.JsonFieldType.STRING
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

internal class CommandReceiverIT : IntegrationTest() {
    val log = LoggerFactory.getLogger(CommandReceiverIT::class.java)

    @Test fun `Can Receive Commands via Post`() {
        this.mockMvc.perform(
            post("/execute-command/this-string")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andDo(
                document("execute-command-post",
                    responseFields(
                        fieldWithPath("timestamp").description("current server timestamp").type(ARRAY),
                        fieldWithPath("output").description("shell output of the script").type(STRING)
                    )
                ))
            .andExpect(content().string(containsString("response-contains-this-string")))
            .andDo {
                log.debug("response: ${it.response.contentAsString}")
            }
    }

    @Test fun `Can Receive Commands via Get`() {
        this.mockMvc.perform(
            get("/execute-command/other-string")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andDo(
                document("execute-command-get",
                    responseFields(
                        fieldWithPath("timestamp").description("current server timestamp").type(ARRAY),
                        fieldWithPath("output").description("shell output of the script").type(STRING)
                    )
                ))
            .andExpect(content().string(containsString("response-contains-the-other-string")))
            .andDo {
                log.debug("response: ${it.response.contentAsString}")
            }
    }

    @Test fun `Can Execute Command List`() {
        this.mockMvc.perform(
            get("/execute-command/chain-command")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andDo(
                document("execute-command-get",
                    responseFields(
                        fieldWithPath("timestamp").description("current server timestamp").type(ARRAY),
                        fieldWithPath("output").description("shell output of the script").type(STRING)
                    )
                ))
            .andExpect(content().string(containsString("command-list-1")))
            .andExpect(content().string(containsString("command-list-2")))
            .andExpect(content().string(containsString("command-list-3")))
            .andDo {
                log.debug("response: ${it.response.contentAsString}")
            }
    }
}
