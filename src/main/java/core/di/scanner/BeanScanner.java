package core.di.scanner;

/**
 * Created by hspark on 2018. 5. 13..
 */
public interface BeanScanner {
	void doScan(Object... basePackages);
}
