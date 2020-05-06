package `in`.ceeq.eventprocessor

import `in`.ceeq.eventannotations.Event
import com.squareup.kotlinpoet.*
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.AnnotationMirror
import javax.lang.model.element.Element
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.TypeElement


class EventProcessor : AbstractProcessor() {

    private lateinit var filer: Filer
    private lateinit var messeger: Messager

    override fun init(p0: ProcessingEnvironment) {
        super.init(p0)
        messeger = p0.messager
        filer = p0.filer
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latestSupported()
    }

    override fun getSupportedAnnotationTypes(): Set<String> {
        return setOf<String>(Event::class.java.canonicalName)
    }

    override fun getSupportedOptions(): MutableSet<String> {
        return super.getSupportedOptions()
    }

    override fun isInitialized(): Boolean {
        return super.isInitialized()
    }

    override fun process(p0: MutableSet<out TypeElement>?, p1: RoundEnvironment?): Boolean {
        p1?.getElementsAnnotatedWith(Event::class.java)?.forEach {
            val typeElement = it as TypeElement
            activitiesWithPackage.put(
                    typeElement.simpleName.toString(),
                    elements.getPackageOf(typeElement).getQualifiedName().toString())
        }

        val greeterClass = ClassName("", "EventTracker")
        val kotlinFile = KotlinFile.builder("", "EventTracker")
                .addType(TypeSpec.classBuilder("EventTracker")
                        .primaryConstructor(FunSpec.constructorBuilder()
                                .addParameter("name", String::class)
                                .build())
                        .addProperty(PropertySpec.builder("name", String::class)
                                .initializer("name")
                                .build())
                        .addFun(FunSpec.builder("greet")
                                .addStatement("println(%S)", "Hello, \$name")
                                .build())
                        .build())
                .addFun(FunSpec.builder("main")
                        .addParameter("args", String::class, KModifier.VARARG)
                        .addStatement("%T(args[0]).greet()", greeterClass)
                        .build())
                .build()

        kotlinFile.writeTo(System.out)
    }

    override fun getCompletions(p0: Element?, p1: AnnotationMirror?, p2: ExecutableElement?, p3: String?):
            MutableIterable<Completion> {
        return super.getCompletions(p0, p1, p2, p3)
    }

}
