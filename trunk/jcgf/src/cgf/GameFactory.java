package cgf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public abstract class GameFactory {
	public static Class play() {
		// new PlayDialog();
		// Constructor<EstadoJogo> c;
		// c.newInstance(initargs)
		// PlayDialog.class.newInstance();

		// return load("D:/eclipse322/workspace/escova/bin/Escova.class");
		return loadJar1("D:/eclipse322/workspace/escova.jar");
	}

	public static Class load(String className) {
		// JarFile jar = null;
		try {
			/*
			 * final JarFile ajar = new JarFile("plugins/" + filename); jar =
			 * ajar; Manifest manifest = ajar.getManifest(); Map map =
			 * manifest.getEntries(); Attributes att =
			 * manifest.getMainAttributes();
			 * 
			 * final String loaded_class_name = att.getValue(classpath); if
			 * (loaded_class_name == null) { System.out.println("can't find
			 * class to load in manifest at the key: " + classpath); }
			 */
			Class loaded_class = new ClassLoader() {
				public Class findClass(String name) {
					System.out.println("ClassLoader -> name: " + name);

					/*
					 * JarEntry loaded_class_entry = ajar.getJarEntry(name); if
					 * (loaded_class_entry == null) { return null; }
					 */
					try {
						InputStream is = new FileInputStream(name);// ajar.getInputStream(loaded_class_entry);
						int avail = is.available();
						byte[] data = new byte[avail];
						is.read(data);
						return defineClass(name.substring(name.lastIndexOf("/") + 1, name.lastIndexOf(".")), data, 0,
								data.length);

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return null;
					}

				}
			}.loadClass(className);// loaded_class_name);

			return loaded_class;

			/*
			 * } catch (IOException e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); return null;
			 */
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	private static Class loadJar(String className) {
		Class loaded_class = null;
		ClassLoader loader = new ClassLoader() {
			public Class findClass(String name) {
				System.out.println("ClassLoader -> name: " + name);
				try {
					InputStream is = new FileInputStream(name);
					int avail = is.available();
					byte[] data = new byte[avail];
					is.read(data);
					return defineClass(name.substring(name.lastIndexOf("/") + 1, name.lastIndexOf(".")), data, 0,
							data.length);
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}
		};
		try {
			JarInputStream jis = new JarInputStream(new FileInputStream(className));
			JarEntry je = jis.getNextJarEntry();
			while (je != null) {
				if (je.getName().endsWith(".class")) {
					File f = new File(je.getName());
					loaded_class = loader.loadClass(je.getName());
					if (EstadoJogoCarta.class.equals(loaded_class.getSuperclass())) {
						System.out.println("achou!");
					}
				}
				je = jis.getNextJarEntry();
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return loaded_class;
	}

	private static Class loadJar1(String className) {
		Class loaded_class = null;
		ClassLoader loader = new ClassLoader() {
			public Class findClass(String name) {
				System.out.println("ClassLoader -> name: " + name);
				try {
					InputStream is = new FileInputStream("D:/eclipse322/workspace/escova.jar/" + name);
					int avail = is.available();
					byte[] data = new byte[avail];
					is.read(data);
					return defineClass(name.substring(name.lastIndexOf("/") + 1, name.lastIndexOf(".")), data, 0,
							data.length);
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}
		};
		try {
			JarInputStream jis = new JarInputStream(new FileInputStream(className));
			JarEntry je = jis.getNextJarEntry();
			while (je != null) {
				if (je.getName().endsWith(".class")) {
					// JarFile k;je.
					// je.getMethod()getExtra()
					// setMethod(method)getSize();
					loaded_class = loader.loadClass(je.getName());
					if (EstadoJogoCarta.class.equals(loaded_class.getSuperclass())) {
						System.out.println("achou!");
					}
				}
				je = jis.getNextJarEntry();
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return loaded_class;
	}
}