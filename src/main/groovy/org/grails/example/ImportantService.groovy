package org.grails.example

import org.springframework.stereotype.Component

import javax.jws.WebMethod
import javax.jws.WebParam
import javax.jws.WebService

@Component
class ImportantServiceImpl implements ImportantService {
    String getImportantData(String type) {
        return "type=$type"
    }
}

@WebService
interface ImportantService {
    @WebMethod()
    String getImportantData(@WebParam(name = 'type') String type)
}
