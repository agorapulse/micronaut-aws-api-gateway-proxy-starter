package agorapulse.business.user

import grails.gorm.annotation.Entity
import groovy.transform.ToString
import org.grails.datastore.gorm.GormEntity

@Entity
@ToString(includes= ['id','name'])
class User implements GormEntity<User> {

    String name

    static constraints = {
        name nullable: false, blank: false, unique: true
    }

    Map toResponse() {
        [
                id: id,
                name: name
        ]
    }

}
