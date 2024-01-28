
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.opencsv.CSVWriter;


public class Almacen {

	public static Scanner sc = new Scanner(System.in);
	public static List<Articulos> listaArticulos = new ArrayList<Articulos>();
	private static final String FILE_PATH = "articulos.dat";
    private static final String CSV_FILE_PATH = "articulos.csv";
    
	public static void main(String[] args) {
		
		cargarArticulosDesdeArchivo();
		pintarMenu();
		
	}
	
	public static void pintarMenu(){
		
		int opcion = 0;
							
		while (opcion !=6){
			System.out.println("Teclea una opción del 1 al 5, 6 para salir");
			System.out.println("1.- Añadir articulo");
			System.out.println("2.- Borrar articulo por id");
			System.out.println("3.- Consultar articulo por id");
			System.out.println("4.- Listar articulos");
			System.out.println("5.- Exportar archivos CSV");
			System.out.println("6.- Salir");
			
			opcion = sc.nextInt();
			
			while(opcion<1 || opcion > 6) {
				System.out.println("del 1 al 6");
				opcion = sc.nextInt();
			}
			
			Scanner leer = new Scanner (System.in);
			switch(opcion) {
			
			case 1:
				Articulos articulo;
				String id, nombre, descripcion;
				int stock;
				double precio;
								
				System.out.println("Introduce el id del articulo: ");
				id = leer.nextLine();
				if (consultarArticuloPorId (id) != null){
					System.out.println ("Ese articulo ya existe");
				}
				else {
				System.out.println("Introduce el nombre: ");
				nombre = leer.nextLine();
				System.out.println("Introduce la descripcion: ");
				descripcion = leer.nextLine();
				System.out.println("Introduce el stock: ");
				stock = leer.nextInt();
				System.out.println("Introduce el precio: ");
				precio = leer.nextInt();
				
				articulo = new Articulos (id, nombre, descripcion, stock, precio);
				añadirArticulo(articulo);
				}
				break; 
				
				
			case 2:
				System.out.println("Introduce el id del articulo: ");
				id = leer.nextLine();
				if (borrarArticuloPorId(id) == null){
				System.out.println("No se ha encontrado el articulo");
				}
				else {
					System.out.println("El articulo ha sido borrado con éxito");
				}
				break;
							
				
			case 3:
				System.out.println("Introduce el id del articulo: ");
				id = leer.nextLine();
				
				Articulos articuloBuscado = consultarArticuloPorId (id);
				if (articuloBuscado == null){
				System.out.println("No se ha encontrado el articulo");
				}
				else {
					System.out.println(articuloBuscado);
				}
				break;
				
				
			case 4: 
				listarArticulos();
				break;
				
				
			case 5:
				exportarArticulosCSV();
				break;
				
				
			case 6:
				
				try(FileOutputStream fichero = new FileOutputStream("almacen.dat", false);
						DataOutputStream  escritor = new DataOutputStream (fichero);) {
					
					for(Articulos a : listaArticulos) {
						//Es interesante usar el metodo adecuado al tipo de dato que queremos guardar
						escritor.writeUTF(a.getId());
						escritor.writeUTF(a.getNombre());//escribimos en UTF-8
						escritor.writeUTF(a.getDescripcion());
						escritor.writeInt(a.getStock());
						escritor.writeDouble(a.getPrecio());
					}
					
					
				}catch (IOException e) {
					System.out.println("No se ha podido abrir el fichero almacen.dat");
					System.out.println(e.getMessage());
					return;//acabamos con nuestro programa
				}
				guardarArticulosEnArchivo();			
				System.out.println("Fin del programa");
			}
		}
		
			
}
	private static void añadirArticulo(Articulos articulo) {
		
		listaArticulos.add(articulo);
		}
		
	
	
	private static Articulos borrarArticuloPorId (String id) {
		try {
			for (Articulos a : listaArticulos) {
				if (a.getId().equals(id)) {
					return listaArticulos.remove(listaArticulos.indexOf(a));
				}
			}
			return null;
			
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}
	private static Articulos consultarArticuloPorId (String id) {
				
			try {	
			Articulos encontrado = null;
			for(Articulos a : listaArticulos) {
				if(a.getId().equals(id)){
					encontrado = a;
				}
			}
			return encontrado;
		} catch (IndexOutOfBoundsException iobe) {
		return null;
		}
	
	}
	
	
	private static void listarArticulos() {
		for (Articulos articulo: listaArticulos) {
			System.out.println(articulo);
		}
	}
	
	private static void exportarArticulosCSV() {
        try (CSVWriter writer = new CSVWriter(new FileWriter(CSV_FILE_PATH))) {
            // Escribir encabezados al archivo CSV
            String[] header = { "Id", "Nombre", "Descripción", "Stock", "Precio" };
            writer.writeNext(header);

            // Escribir datos de los artículos al archivo CSV
            for (Articulos articulo : listaArticulos) {
                String[] data = {
                    String.valueOf(articulo.getId()),
                    articulo.getNombre(),
                    articulo.getDescripcion(),
                    String.valueOf(articulo.getStock()),
                    String.valueOf(articulo.getPrecio())
                };
                writer.writeNext(data);
            }

            System.out.println("Artículos exportados a archivo CSV.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	private static void cargarArticulosDesdeArchivo() {
	
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            listaArticulos = (ArrayList<Articulos>) ois.readObject();
            System.out.println("Articulos cargados desde el archivo.");
        } catch (FileNotFoundException e) {
            System.out.println("El archivo no existe. Se creará uno nuevo al finalizar el programa.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void guardarArticulosEnArchivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(listaArticulos);
            System.out.println("Articulos guardados en el archivo.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

