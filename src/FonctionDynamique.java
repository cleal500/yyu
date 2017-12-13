import java.util.TreeMap;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

public class FonctionDynamique extends TreeMap< Integer, String > implements IntFunction< IntStream > {

	private static final long serialVersionUID = 1L;

	private Function< Integer, String > _defaut;
	
	public FonctionDynamique( Function< Integer, String > a_defaut ) {
		_defaut = a_defaut;
	}
	
	@Override
	public IntStream apply( int a_x ) {
		return ( this.computeIfAbsent( new Integer( a_x ), _defaut ) ).chars();
	}
}
