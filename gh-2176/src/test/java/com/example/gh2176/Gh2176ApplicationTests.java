package com.example.gh2176;

import org.junit.jupiter.api.Test;
import org.neo4j.driver.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class Gh2176ApplicationTests {

    private final Driver driver;
    private final AbstractSuperClassRepository absRepository;
    private final ConcreteSuperClassRepository conRepository;

    @Autowired
    Gh2176ApplicationTests(Driver driver,
                           AbstractSuperClassRepository absRepository,
                           ConcreteSuperClassRepository conRepository) {
        this.driver = driver;
        this.absRepository = absRepository;
        this.conRepository = conRepository;
    }

//    @BeforeEach
//    void setup() {
//        try (Session session = driver.session()) {
//            session.run("MATCH (n) detach delete n").consume();
//        }
//    }

    @Test
    void abstractSuperClass() {
        absRepository.save(new AbstractSuperClass.ExtendsAbstractClassNested());
        absRepository.save(new ExtendsAbstractClassTopLevel());

        List<AbstractSuperClass> domains = absRepository.findAll();
        assertThat(domains).hasSize(2);
        assertThat(domains).allSatisfy(it -> {
            assertThat(it).isOfAnyClassIn(
                    AbstractSuperClass.ExtendsAbstractClassNested.class, ExtendsAbstractClassTopLevel.class);
        });
    }

    @Test
    void concreteSuperClass() {
        conRepository.save(new ConcreteSuperClass.ExtendsConcreteClassNested());
        conRepository.save(new ExtendsConcreteClassTopLevel());

        List<ConcreteSuperClass> domains = conRepository.findAll();
        assertThat(domains).hasSize(2);
        assertThat(domains).allSatisfy(it -> {
            assertThat(it).isOfAnyClassIn(
                    ConcreteSuperClass.ExtendsConcreteClassNested.class, ExtendsConcreteClassTopLevel.class);
        });
    }
}
