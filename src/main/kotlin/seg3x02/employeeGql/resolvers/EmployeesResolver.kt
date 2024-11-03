package seg3x02.employeeGql.resolvers

import org.springframework.stereotype.Controller
import org.springframework.data.mongodb.core.MongoOperations
import seg3x02.employeeGql.entity.Employee
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.Argument
import seg3x02.employeeGql.resolvers.types.CreateEmployeeInput
import seg3x02.employeeGql.repository.EmployeesRepository
import java.util.*

@Controller
class EmployeesResolver(private val employeesRepository: EmployeesRepository, private val mongoOperations: MongoOperations) {
    @QueryMapping
    fun employees(): List<Employee> {
        return employeesRepository.findAll()
    }


    @MutationMapping
    fun newEmployee(@Argument("createEmployeeInput") input: CreateEmployeeInput) : Employee {
        if (input.name != null && 
                input.dateOfBirth != null &&
                input.city != null &&
                input.salary != null) {
            val employee = Employee(input.name, input.dateOfBirth, input.city, input.salary, input.gender, input.email)
            employee.id = UUID.randomUUID().toString()
            employeesRepository.save(employee)
            return employee
        } else {
            throw Exception("Invalid input")
        }
    }
}