package com.environmentalserver.controller




import com.environmentalserver.models.ControllerData
import com.environmentalserver.models.JsonFileManagement
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController




@RestController
@RequestMapping("/receiveAPI")
class ReceiveFromMicrocontroller(private val jsonFileManagement: JsonFileManagement) {
    @PostMapping("/standardReceive")
    fun receive(@RequestBody data: ControllerData): Boolean  {
        println(data)


        return jsonFileManagement.writeToJSONFile(data)
    }
}