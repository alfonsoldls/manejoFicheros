// Paquete que contiene la clase
package test;

// Importaciones de clases necesarias
import modelo.entidad.*;
import java.util.*;
import java.io.*;

// Clase principal que gestiona un almacén de artículos
public class TestAlmacen {

    // Constante para el nombre del archivo donde se guardan los artículos
    public static final String fichero = "articulos.dat";

    // Método principal
    public static void main(String[] args) {
        
        // Creación de archivo para almacenar los datos de los artículos
        File fn = new File(fichero);
        
        // Cargar o inicializar la lista de artículos desde el archivo
        ArrayList<Articulo> listadoArticulos = verificarFichero(fn);
        
        // Variable para almacenar la opción seleccionada por el usuario
        int opcion;
        Scanner sc = new Scanner(System.in);
        
        // Bucle principal del programa
        do {
            // Mostrar el menú de opciones
            menu();
            opcion = sc.nextInt();
            
            // Manejo de las opciones del menú
            switch(opcion) {
            
            case 1:
                // Añadir un nuevo artículo y actualizar el archivo
                listadoArticulos.add(add(listadoArticulos));
                System.out.println("Artículo añadido correctamente.");
                actualizarFichero(fn, listadoArticulos);
                break;
            
            case 2:
                // Borrar un artículo por su ID y actualizar el archivo
                borrar(listadoArticulos);
                actualizarFichero(fn, listadoArticulos);
                break;
            
            case 3:
                // Consultar un artículo por su ID
                consultar(listadoArticulos);
                break;
            
            case 4:
                // Listar todos los artículos
                listar(listadoArticulos);
                break;  
                
            case 5:
                // Exportar la lista de artículos a un archivo CSV
                exportar(listadoArticulos);
                break;
            
            case 6:
                // Salir del programa
                System.out.println("Saliendo del programa...");
                break;
                
            default:
                // Manejar opción no válida
                System.out.println("Opción no válida");
                break;  
            }
            
        } while(opcion != 6);
        
        // Mensaje de finalización del programa
        System.out.println("Programa finalizado.");
        System.exit(0);
    }
	
	public static void menu() {
		System.out.println("");
		System.out.println("-------MENÚ-------");
		System.out.println("Seleccione una de las siguientes opciones:");
		System.out.println("");
		System.out.println("1. Añadir nuevo artículo");
		System.out.println("2. Borrar artículo por id");
		System.out.println("3. Consulta artículo por id");
		System.out.println("4. Listado de todos los artículos");
		System.out.println("5. Exportar artículos a archivo CSV");
		System.out.println("6. Terminar el programa");
		System.out.println("");
	}
	
	// Método estático que recibe un objeto File y retorna una lista de Articulos.
	public static ArrayList<Articulo> verificarFichero(File fn) {

	    // Creación de un ArrayList vacío para almacenar objetos Articulo.
	    ArrayList<Articulo> listado = new ArrayList<Articulo>();

	    // Verificar si el archivo representado por 'fn' existe.
	    if (fn.exists()) {
	        try (// Crear un FileInputStream para leer datos del archivo.
	             FileInputStream fis = new FileInputStream(fn);
	             // Crear un ObjectInputStream para deserializar objetos desde el FileInputStream.
	             ObjectInputStream ois = new ObjectInputStream(fis);) {

	            // Leer y deserializar una lista de objetos Articulo del archivo.
	            List<Articulo> listadoGuardado = (List<Articulo>)ois.readObject();

	            // Iterar sobre cada objeto Articulo en la lista deserializada.
	            for(Articulo articulo : listadoGuardado){
	                // Crear un nuevo objeto Articulo.
	                Articulo a = new Articulo();
	                // Establecer los atributos del nuevo artículo basándose en el artículo leído.
	                a.setId(articulo.getId());
	                a.setNombre(articulo.getNombre());
	                a.setDescripcion(articulo.getDescripcion());
	                a.setStock(articulo.getStock());
	                a.setPrecio(articulo.getPrecio());
	                // Añadir el nuevo artículo al ArrayList 'listado'.
	                listado.add(a);
	            }
	            
	        } catch (FileNotFoundException e) {
	            // Manejo de excepción si el archivo no se encuentra.
	            e.printStackTrace();
	        } catch (IOException e) {
	            // Manejo de excepción para errores de entrada/salida.
	            e.printStackTrace();
	        } catch (ClassNotFoundException e) {
	            // Manejo de excepción si la clase de un objeto serializado no se encuentra.
	            e.printStackTrace();
	        } 
	        
	    }
	    
	    // Retornar la lista de artículos, que puede estar vacía si no se encontró el archivo.
	    return listado;

	}

	
	// Método estático para añadir un nuevo Articulo a la lista proporcionada.
	public static Articulo add(ArrayList<Articulo> listado) {
	    
	    // Crear una nueva instancia de Articulo.
	    Articulo a = new Articulo();
	    // Crear una instancia de Scanner para leer la entrada del usuario.
	    Scanner sc = new Scanner(System.in);
	    // Variable para verificar si el ID ya existe en la lista.
	    boolean encontrado = false;
	    // Variable para almacenar el ID del artículo.
	    int id = 0;

	    // Solicitar al usuario que ingrese los datos del nuevo artículo.
	    System.out.println("Escriba los datos que se indican a continuación sobre el artículo que desea añadir:");
	    System.out.println("ID:");
	    
	    do {
	        // Validar que la entrada es un entero para el ID.
	        while (!sc.hasNextInt()) {
	            System.out.println("Valor incorrecto. Por favor, introduce un número entero para el ID:");
	            sc.next();
	        }
	        
	        // Leer el ID del artículo.
	        id = sc.nextInt();
	        sc.nextLine();
	        
	        // Revisar si el ID ya existe en la lista.
	        for (Articulo f : listado) {
	            if (f.getId() == id) {
	                encontrado = true;
	                System.out.println("El ID introducido ya existe, debe indicar otro diferente");
	                break;
	            }else {
	                encontrado = false;
	            }
	        }
	    
	    } while (encontrado); // Repetir hasta encontrar un ID único.
	    
	    // Establecer el ID del nuevo artículo.
	    a.setId(id);
	    
	    // Solicitar y establecer el nombre del artículo.
	    System.out.println("Nombre:");
	    a.setNombre(sc.nextLine());

	    // Solicitar y establecer la descripción del artículo.
	    System.out.println("Descripción:");
	    a.setDescripcion(sc.nextLine());

	    // Solicitar y validar la entrada para el stock.
	    System.out.println("Stock:");
	    while (!sc.hasNextInt()) {
	        System.out.println("Valor incorrecto. Por favor, introduce un número entero para el Stock:");
	        sc.next();
	    }
	    a.setStock(sc.nextInt());
	    sc.nextLine();

	    // Solicitar y validar la entrada para el precio.
	    System.out.println("Precio:");
	    while (!sc.hasNextFloat()) {
	        System.out.println("Valor incorrecto. Por favor, introduce un número para el Precio:");
	        sc.next();
	    }
	    a.setPrecio(sc.nextFloat());
	    
	    // Retornar el nuevo artículo.
	    return a;
	}

	
	// Método estático para consultar un artículo en la lista proporcionada basado en su ID.
	public static void consultar(ArrayList<Articulo> listado) {
	    // Crear una instancia de Scanner para leer la entrada del usuario.
	    Scanner sc = new Scanner(System.in);
	    // Variable para almacenar el ID ingresado por el usuario.
	    int id;

	    // Bucle infinito para solicitar el ID al usuario hasta recibir un entero válido.
	    while (true) {
	        System.out.println("Introduzca el ID del artículo que desea buscar:");
	        // Comprobar si la entrada es un entero.
	        if (sc.hasNextInt()) {
	            // Si es un entero, almacenar el valor en 'id' y salir del bucle.
	            id = sc.nextInt();
	            break;
	        } else {
	            // Si no es un entero, mostrar un mensaje de error y pedir una nueva entrada.
	            System.out.println("Valor incorrecto. Por favor, introduzca un número entero.");
	            sc.next();
	        }
	    }
	    
	    // Iterar sobre la lista de artículos.
	    for (Articulo a : listado) {
	        
	        // Comprobar si el ID del artículo actual coincide con el ID buscado.
	        if (a.getId() == id) {
	            // Si se encuentra, imprimir los detalles del artículo y salir del método.
	            System.out.println("ID: " + a.getId() + " - " + 
	                    "Nombre: " + a.getNombre() + " - " + 
	                    "Descripción: " + a.getDescripcion() + " - " + 
	                    "Stock: " + a.getStock() + " - " +
	                    "Precio: " + a.getPrecio());
	            return;
	        }
	    }
	    // Si se completa el bucle sin encontrar el artículo, mostrar un mensaje.
	    System.out.println("No se ha encontrado ningún artículo con el ID indicado.");
	}

	
	// Método estático para borrar un artículo de la lista proporcionada basándose en su ID.
	public static ArrayList<Articulo> borrar(ArrayList<Articulo> listado) {
	    
	    // Variable para marcar si se encontró el artículo.
	    boolean encontrado = false;
	    // Crear una instancia de Scanner para leer la entrada del usuario.
	    Scanner sc = new Scanner(System.in);
	    // Variable para almacenar el ID ingresado por el usuario.
	    int id;

	    // Bucle infinito para solicitar el ID al usuario hasta recibir un entero válido.
	    while (true) {
	        System.out.println("Introduzca el ID del artículo que desea borrar:");
	        // Comprobar si la entrada es un entero.
	        if (sc.hasNextInt()) {
	            // Si es un entero, almacenar el valor en 'id' y salir del bucle.
	            id = sc.nextInt();
	            break;
	        } else {
	            // Si no es un entero, mostrar un mensaje de error y pedir una nueva entrada.
	            System.out.println("Valor incorrecto. Por favor, introduzca un número entero.");
	            sc.next();
	        }
	    }
	    
	    // Iterar sobre la lista de artículos.
	    for (Articulo a : listado) {
	        // Comprobar si el ID del artículo actual coincide con el ID a borrar.
	        if (a.getId() == id) {
	            // Si se encuentra, eliminar el artículo de la lista y marcar como encontrado.
	            listado.remove(a);
	            encontrado = true;
	            break;
	        }
	    }
	    
	    // Imprimir mensaje según si se encontró y eliminó el artículo o no.
	    if (encontrado){
	        System.out.println("Artículo eliminado correctamente");
	    } else {
	        System.out.println("No se ha encontrado ningún artículo con el ID indicado.");
	    }
	    
	    // Retornar la lista actualizada.
	    return listado;
	}

	
	
	// Método estático para mostrar en pantalla los detalles de todos los artículos en la lista proporcionada.
	public static void listar(ArrayList<Articulo> listado) {
	    // Iterar sobre cada artículo en la lista.
	    for (Articulo a : listado) {
	        // Imprimir los detalles del artículo actual: ID, nombre, descripción, stock y precio.
	        System.out.println("ID: " + a.getId() + " - " + 
	                           "Nombre: " + a.getNombre() + " - " + 
	                           "Descripción: " + a.getDescripcion() + " - " + 
	                           "Stock: " + a.getStock() + " - " +
	                           "Precio: " + a.getPrecio());
	    }
	}

	
	// Método estático para actualizar o guardar una lista de artículos en un archivo.
	public static void actualizarFichero(File fn, ArrayList<Articulo> listado) {
	    try (// Crear un FileOutputStream para escribir en el archivo representado por 'fn'.
	         FileOutputStream fos = new FileOutputStream(fn);
	         // Crear un ObjectOutputStream para serializar objetos en el FileOutputStream.
	         ObjectOutputStream oos = new ObjectOutputStream(fos)) {

	        // Escribir (serializar) la lista de artículos en el archivo.
	        oos.writeObject(listado);

	    // Atrapar y manejar IOException que podría ocurrir durante la escritura del archivo.
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    } 
	}

	
	// Método estático para exportar una lista de artículos a un archivo CSV.
	public static void exportar(ArrayList<Articulo> listado) {
	    
	    try (// Crear un FileWriter para escribir en un archivo llamado "articulos.csv".
	         FileWriter csv = new FileWriter("articulos.csv");
	         // Envolver el FileWriter en un BufferedWriter para una escritura más eficiente.
	         BufferedWriter buffer = new BufferedWriter(csv);) {
	        
	        // Escribir la cabecera del CSV.
	        buffer.write("ID, Nombre, Descripcion, Stock, Precio");
	        buffer.newLine(); // Nueva línea después de la cabecera.

	        // Iterar sobre cada artículo en la lista.
	        for (Articulo a : listado) {
	            // Escribir los datos de cada artículo en formato CSV.
	            buffer.write(a.getId() + "," + a.getNombre() + "," + a.getDescripcion() + "," + 
	                         a.getStock() + "," + a.getPrecio());
	            buffer.newLine(); // Nueva línea después de cada artículo.
	        }
	    
	        // Mensaje de confirmación al usuario.
	        System.out.println("Archivo CSV exportado correctamente");

	    // Atrapar y manejar IOException que podría ocurrir durante la escritura del archivo.
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}


}
