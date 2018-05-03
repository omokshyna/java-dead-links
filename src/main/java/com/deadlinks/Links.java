package com.deadlinks;

import java.net.URL;
import java.util.Iterator;

public interface Links extends Iterable<URL> {
    Iterator<URL> iterator();
}
