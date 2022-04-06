package io.holixon.avro.maven.spoon

import io.holixon.avro.maven.avro.DDDType
import io.holixon.avro.maven.spoon.ext.addStringValue
import io.holixon.avro.maven.spoon.ext.annotation
import org.apache.avro.specific.SpecificRecordBase
import org.jmolecules.event.annotation.DomainEvent
import spoon.reflect.declaration.CtAnnotation
import spoon.reflect.declaration.CtClass
import spoon.reflect.declaration.CtElement

class JMoleculesDomainEventAnnotationProcessor(context: SpoonContext) : AbstractSpecificRecordProcessor(context) {

  override fun isToBeProcessed(candidate: CtClass<out SpecificRecordBase>) = isGeneratedSpecificRecordClass
    .and(hasRuntimeDependency("org.jmolecules", "jmolecules-events"))
    .and(hasMetaDataType(DDDType.event)).test(candidate)


  override fun process(element: CtClass<out SpecificRecordBase>) {
    val meta = context.metaData(element)

    val annotation: CtAnnotation<DomainEvent> = factory.annotation<DomainEvent>().apply {
      addStringValue("namespace", meta.namespace)
      addStringValue("name", meta.name)
    }

    element.addAnnotation<CtElement>(annotation)

    logger.info("""${element.qualifiedName}:  added $annotation""")
  }

}
