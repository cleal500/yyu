import javax.swing.*;
import java.util.function.BiFunction;
import java.util.stream.Collector;
import java.util.stream.IntStream;

public class LSystem implements BiFunction< Integer, String, String > {

	private static final Collector< Integer, ?, String > _TO_STRING; 
	
	static {
		_TO_STRING = Collector.of(
	        StringBuilder::new,
	        StringBuilder::appendCodePoint,
	        StringBuilder::append,
	        StringBuilder::toString );
	}
	
	private FonctionDynamique _fd;

	public LSystem() {
		_fd = new FonctionDynamique( x -> new String( Character.toChars( x ) ) );



	}
	
	public void ajouterRegle( char c, String r ) {
		_fd.put( (int)c, r );
	}
	
	public void enleverRegle( char c ) {
		_fd.remove( (int)c );
	}
	
	public void effacerRegles() {
		_fd.clear();
	}
	
	private IntStream installerFonction( IntStream s, int n ) {
		return ( n > 0 ) ? installerFonction( s.flatMap( _fd ), n - 1 ) : s ; 
	}
	
	@Override
	public String apply( Integer n, String axiome ) {
		IntStream s = axiome.chars();
		
		return installerFonction( s, n ).boxed().collect( _TO_STRING );
	}
}
