package starter

import grails.gorm.services.Service

@Service(User)
interface UserDataService {

    List<User> findAll()

    User save(String name)

    User save(User user)

    User findByName(String name)

    User findById(Serializable id)

    void delete(Serializable id)

}
