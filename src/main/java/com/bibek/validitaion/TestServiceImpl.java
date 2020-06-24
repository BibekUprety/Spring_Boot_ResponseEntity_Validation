package com.bibek.validitaion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TestServiceImpl implements TestService {

    private TestRepository repository;

    @Autowired
    public TestServiceImpl(TestRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TestModel> getAll(TestModel testModel) {
        return repository.findAll();
    }

    @Override
    public void update(Long id, TestModel testModel) {
          repository.save(testModel);
//         we can do by update the data by this way also
        repository.findById(id).map(testModel1 -> {
            testModel1.setName(testModel.getName());
            testModel1.setPassword(testModel.getPassword());
            return repository.save(testModel1);
        });
    }



    @Override
    public void  deleteById(Long id) {
         repository.deleteById(id);

    }

    @Override
    public Optional<TestModel> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void addTestModel(TestModel testModel) {
        repository.save(testModel);
    }
}
