package com.bibek.validitaion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("test")
public class TestController {

    private TestServiceImpl testService;
    @Autowired
    public TestController(TestServiceImpl testService) {
        this.testService = testService;
    }

    /*
    for postman to check

      {

        "name": "bibek",
        "password": "bibek"
    }



     */

    @PostMapping
    @ResponseBody
    public ResponseEntity<String> addTest(@RequestBody @Valid TestModel testModel){
        testService.addTestModel(testModel);
        return new ResponseEntity<String>("new data is created",HttpStatus.CREATED);

    }


    @GetMapping
    @ResponseBody
    public ResponseEntity<List<TestModel>> getAll(TestModel testModel){
        List<TestModel> list = testService.getAll(testModel);
        if (list.isEmpty()){
            throw new RecordNotFoundException("No data Found");

        }
       return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Optional<TestModel>> getById(@PathVariable(name = "id")Long id){
        Optional<TestModel> testModelOptional =testService.getById(id);
        if (testModelOptional.isPresent()){
            return new ResponseEntity<>(testModelOptional,HttpStatus.FOUND);
        }
        else {
            throw new RecordNotFoundException("no data found with that id" +id);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteById(@Valid @PathVariable(name = "id")Long id){
       Optional<TestModel> test1= testService.getById(id);
        if (test1.isPresent()){
            testService.deleteById(id);
            return new ResponseEntity<String>("the data is deleted",HttpStatus.RESET_CONTENT);
        }
        else {
            throw  new RecordNotFoundException("the given id is invalid"+id);
        }
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<String> update(@PathVariable(name = "id")Long id,@RequestBody TestModel testModel){
        Optional<TestModel> testModel1 =testService.getById(id);
        if (testModel1.isPresent()) {
            testService.update(id, testModel);
            return new ResponseEntity<String>("the data is updated",HttpStatus.RESET_CONTENT);
        }
        throw  new RecordNotFoundException("the given is is invalid"+id);
    }
}
