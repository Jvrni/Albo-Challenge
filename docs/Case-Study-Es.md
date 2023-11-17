# Caso de Estudio

### Presente un escenario específico (por ejemplo, una aplicación de lista de tareas pendientes) y pida al candidato que describa cómo estructuraría la aplicación utilizando MVVM, LiveData, Room, Koin y Retrofit.
### Pueden incluir diagramas de arquitectura y fragmentos de código para explicar su enfoque.

**Contextos**

En esta sección, presentamos un caso de uso completo para demostrar cómo estructurar una aplicación.
Imagine que estamos creando una interfaz de usuario que muestra una lista de tareas al usuario. Usamos una base de datos local para buscar y guardar datos para una tarea determinada.


**Descripción general**

Comience revisando el siguiente diagrama, que muestra cómo todos los módulos deben interactuar entre sí después de crear la aplicación.

![Diagrama](images/diagram-case-study.png)

Tenga en cuenta que cada componente depende únicamente del que está un nivel por debajo de él. Por ejemplo, los componentes de la interfaz de usuario solo dependen del modelo de vista. El repositorio es la única clase que depende de varias otras clases. En este ejemplo, el repositorio se basa en un modelo de datos persistente.

**Crear la interfaz de usuario**

La interfaz de usuario puede consistir en una actividad, un fragmento o una pantalla componible.

- **Ejemplos de Activity UI**

```
class TasksActivity : AppCompatActivity() {
    private val viewModel by viewModel<TasksViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityTasksBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        viewModel.tasks.observe(this) { result -> 
            // update UI
        }
    }
}
```

- **Ejemplos de Fragment UI**

```
class TasksFragment : Fragment() {
    private val viewModel by viewModel<TasksViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareObservers()
    }
    
    private fun prepareObservers() {
        viewModel.tasks.observe(viewLifecycleOwner) { result -> 
            // update UI
        }
    }
}
```

- **Ejemplos de Compose UI**

```
class TasksActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            val tasks = remember { mutableStateOf(Tasks()) }
        
            val viewModel = koinViewModel<TasksViewModel>()
            viewModel.tasks.observe(this) { result -> tasks.value = result }
        
            MaterialTheme {
                TasksScreen(
                    viewmodel,
                    tasks
                )
            }
        }
    }
}
```


- **Ejemplos de ViewModel**

Usamos un TasksViewModel basado en el componente de arquitectura ViewModel para obtener datos.

```
class MainViewModel(private val repository: TasksRepository) : ViewModel() {

    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> = _tasks
    
    fun getTasks() {
        repository.getTasks()
            .onEach { result ->
                _tasks.value = result
            }
            .catch {
                // todo error case
            }
            .launchIn(viewModelScope)
    }
```


**Obtener datos**

Ahora que hemos usado LiveData para conectar ViewModel a la interfaz de usuario, ¿cómo podemos recuperar los datos de las tareas?

Los módulos del repositorio manejan operaciones de datos. Proporcionan una API limpia para que el resto de la aplicación pueda recuperar estos datos fácilmente. Saben dónde recopilar los datos y qué llamadas API deben realizarse cuando se actualizan los datos. Puede pensar en los repositorios como mediadores entre diferentes fuentes de datos, por ejemplo, modelos persistentes, servicios web y cachés.

```
interface TasksRepository {
    fun getTasks() : Flow<List<Task>>
}

class TasksRepositoryImpl(private val taskDao: TaskDao): TasksRepository {
    override fun getTasks() = flow {
        emit(taskDao.getTasks())
    }
}
```

**Persistir en los datos**

Room es una biblioteca de mapeo de objetos que proporciona persistencia de datos locales con un uso mínimo de código repetitivo. En tiempo de compilación, valida cada consulta con su esquema de datos, por lo que las consultas SQL rotas dan como resultado errores en tiempo de compilación en lugar de fallas en tiempo de ejecución. Room abstrae algunos de los detalles de implementación subyacentes del trabajo con tablas y consultas SQL sin formato. También le permite observar cambios en los datos de la base de datos (incluidas colecciones y consultas de combinación) al exponer esos cambios a través de objetos LiveData. Esta biblioteca incluso define explícitamente restricciones de ejecución que abordan problemas comunes de subprocesos, como el acceso al almacenamiento en el subproceso principal.

Para usar Room, necesitamos definir nuestro esquema local. Primero, agregamos la anotación @Entity a nuestra clase de modelo de datos Task y una anotación @PrimaryKey al campo id de la clase. Estas anotaciones marcan Task como una tabla en nuestra base de datos y id como la clave principal de la tabla:

- **Task**

```
@Entity
data class Task(
   @PrimaryKey private val id: String,
   private val title: String,
   private val description: String
)
```

A continuación, creamos una clase de base de datos que implementa RoomDatabase para nuestra aplicación:

- **TaskDatabase**

```
@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {
   abstract fun taskDao(): TaskDao
}
```

Tenga en cuenta que TaskDatabase es abstracta. Room lo implementa automáticamente. Para obtener más detalles, consulte la [Documentación de Room](https://developer.android.com/training/data-storage/room).

Ahora necesitamos una forma de insertar datos de tareas en la base de datos. Para esta tarea, creamos un objeto de acceso a datos (DAO).

- **TaskDao**

```
@Dao
interface TaskDao {
   @Insert(onConflict = REPLACE)
   fun save(task: Task)

   @Query("SELECT * FROM task)
   fun getTasks(): Flow<List<Task>>
}
```

Tenga en cuenta que el método getTasks devuelve un objeto de tipo Flow<List<Task>. Usar Flow with Room te permite recibir actualizaciones en tiempo real. Esto significa que siempre que haya un cambio en la tabla de Task, se emitirá una nueva Task.

**Inyección de dependencia de Koin**

 - **Configuración de Gradle**
   
Agregue la dependencia Koin de Android como se muestra a continuación:

```
dependencies {

    // Koin for Android
    implementation "io.insert-koin:koin-android:$koin_version"
}
```

 - **El módulo Koin**
   
Utilice la función de módulo para declarar un módulo Koin. Un módulo Koin es el lugar donde definimos todos nuestros componentes a inyectar.

```
val appModule = module {
    
}
```

Declaremos nuestros componentes.

Creando una instancia de ViewModel.
Creando una instancia de Dao.
Y queremos un singleton de TaskRepository, creando una instancia de TaskRepositoryImpl

```
val appModule = module {
    viewModelOf(::TaskViewModel)
    
    single {
        Room.databaseBuilder(
            androidApplication,
            TaskDatabase::class.java,
            "taskDatabase"
        ).build()
    }
    
    single { get<TaskDatabase>().taskDao() }

    single<TaskRepository> { TaskRepositoryImpl() }
}
```

- **Iniciar Koin**

Necesitamos iniciar Koin con nuestra aplicación de Android. Simplemente llame a la función startKoin() en el punto de entrada principal de la aplicación, nuestra clase App:

```
class App : Application(){
    override fun onCreate() {
        super.onCreate()
        
        startKoin{
            androidLogger()
            androidContext(this@App)
            modules(appModule)
        }
    }
}
```
