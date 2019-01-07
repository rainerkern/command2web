package systemkern.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import systemkern.entity.PongDTO

@RestController
@RequestMapping("/default/ping")
internal class PingController {

    @GetMapping
    fun ping(): PongDTO {
        return PongDTO()
    }
}
