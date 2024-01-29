package main;

import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import modelo.javabean.Articulo;

public class Main {

	static Scanner sc = new Scanner(System.in);
	
	
	public static void main(String[] args) {
		
		ArrayList<Articulo> listaArticulos = checkearArticulos();
		boolean salir = false;
		String opc;
		
		while(!salir) {
			
			mostarMenu();
			opc = sc.nextLine();
			switch(opc) {
				case "1": 
					listaArticulos.add(añadirArticulo(listaArticulos)) ;
					break;
				case "2":
					listaArticulos.remove(borraArticulo(listaArticulos));
					break;
				case "3":
					consultaArticulo(listaArticulos);
					break;
				case "4": 
					listarArticulos(listaArticulos);
					break;
				case "5":
					exportar(listaArticulos);
					break;
				case "6":
					crearArchivo(listaArticulos); 
					salir = true;
					break;
				default: 
					System.out.println("Introduzca una opcion valida");
				
			} 	
			
		}
		System.out.println("Cerrando programa");
	}
	
	private static void exportar(ArrayList<Articulo> listaArticulos) {
		File csv = new File("articulos.csv");
		
		try(FileWriter fw = new FileWriter(csv);
			BufferedWriter bw = new BufferedWriter(fw)) {
			bw.write("Id,Nombre,Descripcion,Stock,Precio");
			bw.newLine();
			
			for (Articulo a : listaArticulos) {
				bw.write(a.getId() + "," + a.getNombre() + "," + a.getDescripcion() + "," + 
                        a.getStock() + "," + a.getPrecio());
				bw.newLine();
			}
			
			System.out.println("Archivo CSV exportado correctamente");

			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private static void listarArticulos(ArrayList<Articulo> listaArticulos) {
		for (Articulo a : listaArticulos) {
			System.out.println(a);
		}
	}


	private static void crearArchivo(ArrayList<Articulo> listaArticulos) {
		File file = new File("articulos.dat");
		
		try (FileOutputStream fos = new FileOutputStream(file);
			 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(listaArticulos);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	private static Articulo borraArticulo(ArrayList<Articulo> lista) {
		Articulo articulo = new Articulo();
		System.out.println("Introduce el id del Articulo");
		String id = sc.nextLine();
		articulo.setId(id);
		if(!lista.contains(articulo)) {
			System.out.println("No se ha encontrado ningun articulo con ese Id");
		}
		return articulo;
		
	}
	
	private static void consultaArticulo(ArrayList<Articulo> lista) {
		Articulo articulo = new Articulo();
		System.out.println("Introduce el id del Articulo");
		String id = sc.nextLine();
		articulo.setId(id);
		if(!lista.contains(articulo)) {
			System.out.println("No se ha encontrado ningun articulo con ese Id");
		}else {
			System.out.println(lista.get(lista.indexOf(articulo)));
		}
	}
	
	private static Articulo añadirArticulo(ArrayList<Articulo> lista) {
		Articulo articulo = new Articulo();
		boolean idrepetido;
		do {
			idrepetido = false;
			System.out.println("Introduce el id del Articulo");
			String id = sc.nextLine();
			articulo.setId(id);
			if(lista.contains(articulo)) {
				System.out.println("Id del articulo repetido");
				idrepetido = true;
			}	
		}while(idrepetido);
		System.out.println("Introduce el nombre del Articulo");
		articulo.setNombre(sc.nextLine());
		System.out.println("Introduce la descripcion del Articulo");
		articulo.setDescripcion(sc.nextLine());
		System.out.println("Introduce la cantidad de stock del articulo");
		articulo.setStock(Integer.parseInt(sc.nextLine()));
		System.out.println("Introduce el precio del articulo");
		articulo.setPrecio(Double.parseDouble(sc.nextLine()));
		
		return articulo;
	}
	
	private static ArrayList<Articulo> checkearArticulos(){
		
		ArrayList<Articulo> articulos = new ArrayList<>();
		
		File archivo = new File("articulos.dat");
		
		if(archivo.exists()) {
			try(FileInputStream fis = new FileInputStream(archivo);
				ObjectInputStream ois = new ObjectInputStream(fis);) {
					articulos = new ArrayList<>();
					boolean eof = false;
					while(!eof) {
						try {
							articulos = (ArrayList<Articulo>) ois.readObject();
							
							
						} catch (EOFException e1) {
							eof = true;
						} catch (IOException e2) {
							System.out.println("Error al leer los contactos de la agenda");
							System.out.println(e2.getMessage());
						} catch (ClassNotFoundException e3) {
							System.out.println("La clase Contacto no está cargada en memoria");
							System.out.println(e3.getMessage());
						}
					}
							
			}catch (IOException e) {
					System.out.println("Error al leer en el fichero");
					e.printStackTrace();
			}
			
		}
		return articulos;
	}
		
	
	private static void mostarMenu() {
		
		System.out.println("-------MENÚ-------\n\n");
		System.out.println("1. Añadir nuevo artículo");
		System.out.println("2. Borrar artículo por id");
		System.out.println("3. Consulta artículo por id");
		System.out.println("4. Listado de todos los artículos");
		System.out.println("5. Exportar artículos a archivo CSV");
		System.out.println("6. Terminar el programa\n");
	}
	
	
	
}
