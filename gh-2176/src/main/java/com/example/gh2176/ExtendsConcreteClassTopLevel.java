package com.example.gh2176;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Node;

@Getter
@Setter
@Node
public class ExtendsConcreteClassTopLevel extends ConcreteSuperClass {
}
