/*
 * Copyright (c) 2005-2010 Substance Kirill Grouchnikov. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 * 
 *  o Redistributions of source code must retain the above copyright notice, 
 *    this list of conditions and the following disclaimer. 
 *     
 *  o Redistributions in binary form must reproduce the above copyright notice, 
 *    this list of conditions and the following disclaimer in the documentation 
 *    and/or other materials provided with the distribution. 
 *     
 *  o Neither the name of Substance Kirill Grouchnikov nor the names of 
 *    its contributors may be used to endorse or promote products derived 
 *    from this software without specific prior written permission. 
 *     
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, 
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR 
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR 
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; 
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, 
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. 
 */
package test.samples.lafwidget.clientprop;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import org.pushingpixels.lafwidget.LafWidget;
import org.pushingpixels.lafwidget.preview.DefaultPreviewPainter;
import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.pushingpixels.substance.api.skin.BusinessBlackSteelSkin;

/**
 * Test application that shows the use of the
 * {@link LafWidget#COMPONENT_PREVIEW_PAINTER} client property.
 * 
 * @author Kirill Grouchnikov
 * @see LafWidget#COMPONENT_PREVIEW_PAINTER
 */
public class ComponentPreviewPainter extends JFrame {
	/**
	 * Creates the main frame for <code>this</code> sample.
	 */
	public ComponentPreviewPainter() {
		super("Component preview painter");

		this.setLayout(new BorderLayout());

		// Create panel with custom painting logic - simple
		// diagonal fill.
		JPanel samplePanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D graphics = (Graphics2D) g.create();
				graphics
						.setPaint(new GradientPaint(0, 0, new Color(100, 100,
								255), getWidth(), getHeight(), new Color(255,
								100, 100)));
				graphics.fillRect(0, 0, getWidth(), getHeight());
				graphics.dispose();
			}
		};
		samplePanel.setPreferredSize(new Dimension(800, 400));
		samplePanel.setSize(this.getPreferredSize());
		samplePanel.setMinimumSize(this.getPreferredSize());

		final JScrollPane scrollPane = new JScrollPane(samplePanel);
		this.add(scrollPane, BorderLayout.CENTER);

		JPanel controls = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		final JCheckBox hasPreview = new JCheckBox("scroll has preview");
		hasPreview.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
                    public void run() {
						scrollPane
								.putClientProperty(
										LafWidget.COMPONENT_PREVIEW_PAINTER,
										hasPreview.isSelected() ? new DefaultPreviewPainter()
												: null);
						repaint();
					}
				});
			}
		});
		controls.add(hasPreview);
		this.add(controls, BorderLayout.SOUTH);

		this.setSize(400, 200);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * The main method for <code>this</code> sample. The arguments are ignored.
	 * 
	 * @param args
	 *            Ignored.
	 */
	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		SwingUtilities.invokeLater(new Runnable() {
			@Override
            public void run() {
				UIManager.put(SubstanceLookAndFeel.SHOW_EXTRA_WIDGETS,
						Boolean.TRUE);
				SubstanceLookAndFeel.setSkin(new BusinessBlackSteelSkin());
				new ComponentPreviewPainter().setVisible(true);
			}
		});
	}
}
