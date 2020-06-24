package com.bibek.validitaion;

import java.util.List;
import java.util.Optional;

public interface TestService {

    void addTestModel(TestModel testModel);

    List<TestModel> getAll(TestModel testModel);


    Optional<TestModel> getById(Long id);

    void deleteById(Long id);

    void update(Long id, TestModel testModel);
}
