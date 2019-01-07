package systemkern.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import systemkern.entity.EchoDTO
import javax.validation.Valid

@RestController
@RequestMapping("/default/echo")
internal class EchoController {

    @PostMapping
    fun echo(@Valid @RequestBody value: EchoDTO) = value

}
