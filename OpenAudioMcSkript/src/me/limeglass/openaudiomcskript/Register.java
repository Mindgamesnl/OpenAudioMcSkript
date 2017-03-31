package me.limeglass.openaudiomcskript;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.Classes;
import me.limeglass.openaudiomcskript.Utils.*;

public class Register {
	
	private static ArrayList<String> events = new ArrayList<>();
	private static ArrayList<String> conditions = new ArrayList<>();
	private static ArrayList<String> effects = new ArrayList<>();
	private static ArrayList<String> expressions = new ArrayList<>();
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected static void main() {
		try {
			Set<Class<?>> classes = new HashSet<>();		
			Method method = JavaPlugin.class.getDeclaredMethod("getFile");
			
			method.setAccessible(true);
			File file = (File) method.invoke(OpenAudioMcSkript.getInstance());
			try {
				JarFile jar = new JarFile(file);
				for (Enumeration<JarEntry> entry = jar.entries(); entry.hasMoreElements();) {
					String name = entry.nextElement().getName().replace("/", ".");
					if (name.startsWith("me.limeglass.openaudiomcskript") && name.endsWith(".class")) {
						try {
							classes.add(Class.forName(name.substring(0, name.length() - 6)));
						} catch (ClassNotFoundException error) {
							error.printStackTrace();
						} catch (NoClassDefFoundError | ExceptionInInitializerError e) {}
					}
				}
				jar.close();
			} catch(Exception error) {
				error.printStackTrace();
			}
			if (classes != null) {
				run: for (Class c : classes) {
					if (c.isAnnotationPresent(Disabled.class)) {
						continue run;
					}
					if (c.isAnnotationPresent(Syntax.class)) {
						String[] syntax = ((Syntax) c.getAnnotation(Syntax.class)).value();
						if (ch.njol.skript.lang.Effect.class.isAssignableFrom(c)) {
							Skript.registerEffect(c, syntax);
							for (String s : syntax) {
								effects.add(s);
							}
						} else if (Condition.class.isAssignableFrom(c)) {
							Skript.registerCondition(c, syntax);
							for (String s : syntax) {
								conditions.add(s);
							}
						} else if (Expression.class.isAssignableFrom(c)) {
							if (c.isAnnotationPresent(PropertyType.class)) {
								if (c.isAnnotationPresent(RegisterEnum.class)) {
									if (Classes.getExactClassInfo(((Expression) c.newInstance()).getReturnType()) == null) {
										EnumClassInfo.create(((Expression) c.newInstance()).getReturnType(), ((RegisterEnum) c.getAnnotation(RegisterEnum.class)).value()).register();
									}
								}
								for (ExpressionType et : ExpressionType.values()) {
									if (et.name().equals(((PropertyType) c.getAnnotation(PropertyType.class)).value())) {
										try {
											Skript.registerExpression(c, ((Expression) c.newInstance()).getReturnType(), et, syntax);
											for (String s : syntax) {
												expressions.add(s);
											}
										} catch (IllegalAccessException e) {
											Bukkit.getConsoleSender().sendMessage(OpenAudioMcSkript.cc(OpenAudioMcSkript.prefix + "&cFailed to register expression " + c.getCanonicalName()));
											e.printStackTrace();
										} catch (InstantiationException e) {
											Bukkit.getConsoleSender().sendMessage(OpenAudioMcSkript.cc(OpenAudioMcSkript.prefix + "&cFailed to register expression " + c.getCanonicalName()));
											e.printStackTrace();
										}
									}
								}
							}
						}
					}
				}
			}
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e2) {
			e2.printStackTrace();
		}
		OpenAudioMcSkript.getInstance().getConfig().set("Syntax.Effects", conditions);
		OpenAudioMcSkript.getInstance().getConfig().set("Syntax.Effects", expressions);
		OpenAudioMcSkript.getInstance().getConfig().set("Syntax.Effects", effects);
		//OpenAudioMcSkript.getInstance().getConfig().set("Syntax.Effects", events);
		OpenAudioMcSkript.saveMainConfig();
		OpenAudioMcSkript.debugMessage("Registered &b" + effects.size() + " &fEffects, &b" + conditions.size() + " &fConditions and &b" + expressions.size() + " &fExpressions");
	}
	
	public static void events(){
	}
	
	@SuppressWarnings("unchecked")
	public static void registerEvent(@SuppressWarnings("rawtypes") Class clazz, String syntax) {
		Skript.registerEvent(syntax, SimpleEvent.class, clazz, syntax);
		events.add(syntax);
	}
	
	public static void metrics(Metrics metrics) {
		metrics.addCustomChart(new Metrics.SimplePie("openaudiomc_version") {
			@Override
			public String getValue() {
				return Bukkit.getPluginManager().getPlugin("OpenAudio").getDescription().getVersion();
			}
		});
	}
}