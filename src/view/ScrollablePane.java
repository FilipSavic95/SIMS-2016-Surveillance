package view;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Rectangle;

import javax.swing.JViewport;
import javax.swing.Scrollable;

public class ScrollablePane extends MyPanel implements Scrollable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ScrollablePane() {
		addClickListeners();
	}

	public ScrollablePane(LayoutManager layout) {
		super(layout);
		addClickListeners();
	}

	@Override
	public Dimension getPreferredScrollableViewportSize() {
		return new Dimension(100, 100);
	}

	@Override
	public int getScrollableUnitIncrement(Rectangle visibleRect,
			int orientation, int direction) {
		return 32;
	}

	@Override
	public int getScrollableBlockIncrement(Rectangle visibleRect,
			int orientation, int direction) {
		return 32;
	}

	@Override
	public boolean getScrollableTracksViewportWidth() {
		boolean track = false;
		Container parent = getParent();
		if (parent instanceof JViewport) {
			JViewport vp = (JViewport) parent;
			track = vp.getWidth() > getPreferredSize().width;
		}
		return track;
	}

	@Override
	public boolean getScrollableTracksViewportHeight() {
		boolean track = false;
		Container parent = getParent();
		if (parent instanceof JViewport) {
			JViewport vp = (JViewport) parent;
			track = vp.getHeight() > getPreferredSize().height;
		}
		return track;
	}

	protected void addClickListeners() {
		
	}

}