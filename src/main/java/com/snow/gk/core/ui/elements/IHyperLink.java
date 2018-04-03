package com.snow.gk.core.ui.elements;

public interface IHyperLink extends IElement {
    String getHref();
    String getTarget();
    void clickLink();
}
