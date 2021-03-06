 /**
 * Copyright (c) 2010 Intelligent Software Solutions
 * Unpublished-all rights reserved under the copyright laws of the United States.
 *
 * This software was developed under sponsorship from the
 * Air Force Research Laboratory under FA8750-06-D-005 and FA8750-09-R-0022.
 *
 * Contractor: Intelligent Software Solutions
 *             5450 Tech Center Drive, Suite 400
 *             Colorado Springs, 80919
 *             http://www.issinc.com
 *
 * Intelligent Software Solutions has title to the rights in this computer
 * software. The Government's rights to use, modify, reproduce, release, perform,
 * display, or disclose these technical data are restricted by paragraph (b)(1) of
 * the Rights in Technical Data-Noncommercial Items clause contained in
 * Contract No. FA8750-06-D-005 and No. FA8750-09-R-0022.
 * Any reproduction of technical data or portions thereof marked with this legend
 * must also reproduce the markings.
 *
 * Intelligent Software Solutions does not grant permission inconsistent with the
 * aforementioned unlimited government rights to use, disclose, copy, or make
 * derivative works of this software to parties outside the Government.
 */

 package org.pushingpixels.substance.api.skin;

import org.pushingpixels.substance.api.*;
import org.pushingpixels.substance.api.colorscheme.BaseColorScheme;
import org.pushingpixels.substance.api.colorscheme.SteelBlueColorScheme;
import org.pushingpixels.substance.api.painter.border.GlassBorderPainter;
import org.pushingpixels.substance.api.painter.decoration.ArcDecorationPainter;
import org.pushingpixels.substance.api.painter.fill.ClassicFillPainter;
import org.pushingpixels.substance.api.painter.highlight.GlassHighlightPainter;
import org.pushingpixels.substance.api.painter.overlay.BottomLineOverlayPainter;
import org.pushingpixels.substance.api.shaper.ClassicButtonShaper;
import org.pushingpixels.substance.internal.colorscheme.BlendBiColorScheme;

import javax.swing.UIManager;
import java.awt.Color;

/**
 * <code>Cerulean</code> skin.
 */
public class CeruleanSkin extends SubstanceSkin {
    /**
     * Display name for <code>this</code> skin.
     */
    public static final String NAME = "Cerulean";


    /**
     * Overlay painter to paint separator lines on some decoration areas.
     */
    private BottomLineOverlayPainter bottomLineOverlayPainter;

    /**
     * Creates a new <code>Nebulous</code> skin.
     */
    public CeruleanSkin() {
        super();

        ColorSchemes schemes = SubstanceSkin
                .getColorSchemes("org/pushingpixels/substance/api/skin/nebula.colorschemes");

        SubstanceColorScheme activeScheme = schemes.get("Nebula Active");
        SubstanceColorScheme enabledScheme = schemes.get("Nebula Enabled").saturate(-0.9);
        SubstanceColorScheme rolloverUnselectedScheme = schemes
                .get("Nebula Rollover Unselected");
        final SubstanceColorScheme pressedScheme = schemes.get("Nebula Pressed");
        SubstanceColorScheme rolloverSelectedScheme = schemes
                .get("Nebula Rollover Selected");
        SubstanceColorScheme disabledScheme = schemes.get("Nebula Disabled").saturate(-0.9);

        SubstanceColorSchemeBundle defaultSchemeBundle = new SubstanceColorSchemeBundle(
                activeScheme, enabledScheme, disabledScheme);

        CopyMutableColorScheme steelBlue= new CopyMutableColorScheme("Cerulean Hover", new SteelBlueColorScheme().tint(0.4));
        steelBlue.setForegroundColor(enabledScheme.getForegroundColor());

        double saturate = 0.1;
        double tint = 0.4;
        double shade = tint/4;
        CopyMutableColorScheme pressed = new CopyMutableColorScheme("Cerulean Pressed", steelBlue.saturate(saturate).shade(shade));
        //pressed.setForegroundColor(pressedScheme.getForegroundColor());
        defaultSchemeBundle.registerColorScheme(pressed,
                ComponentState.PRESSED_SELECTED, ComponentState.PRESSED_UNSELECTED);
        defaultSchemeBundle.registerColorScheme(new BlendBiColorScheme(
                        steelBlue, disabledScheme, 0.25),
                ComponentState.DISABLED_SELECTED);
        defaultSchemeBundle.registerColorScheme(
                steelBlue.tint(tint).saturate(saturate),
                ComponentState.SELECTED);
        defaultSchemeBundle.registerColorScheme(
                steelBlue.shade(shade / 2).saturate(saturate/2),
                ComponentState.ROLLOVER_SELECTED);
        defaultSchemeBundle.registerColorScheme(
                steelBlue.tint(tint / 2).saturate(saturate/2),
                ComponentState.ROLLOVER_UNSELECTED);
        defaultSchemeBundle.registerColorScheme(steelBlue.shade(0.5),
                ColorSchemeAssociationKind.MARK, ComponentState.getActiveStates());
        defaultSchemeBundle.registerColorScheme(steelBlue,
                ColorSchemeAssociationKind.BORDER, ComponentState.getActiveStates());

        // for progress bars
        ComponentState determinateState = new ComponentState(
                "determinate enabled", new ComponentStateFacet[] {
                        ComponentStateFacet.ENABLE,
                        ComponentStateFacet.DETERMINATE,
                        ComponentStateFacet.SELECTION }, null);
        ComponentState determinateDisabledState = new ComponentState(
                "determinate disabled", new ComponentStateFacet[] {
                        ComponentStateFacet.DETERMINATE,
                        ComponentStateFacet.SELECTION },
                new ComponentStateFacet[] { ComponentStateFacet.ENABLE });
        ComponentState indeterminateState = new ComponentState(
                "indeterminate enabled",
                new ComponentStateFacet[] { ComponentStateFacet.ENABLE,
                        ComponentStateFacet.SELECTION },
                new ComponentStateFacet[] { ComponentStateFacet.DETERMINATE });
        ComponentState indeterminateDisabledState = new ComponentState(
                "indeterminate disabled", null, new ComponentStateFacet[] {
                        ComponentStateFacet.DETERMINATE,
                        ComponentStateFacet.ENABLE, ComponentStateFacet.SELECTION });
        defaultSchemeBundle.registerColorScheme(rolloverSelectedScheme,
                determinateState, indeterminateState);
        defaultSchemeBundle.registerColorScheme(rolloverSelectedScheme,
                ColorSchemeAssociationKind.BORDER,
                determinateState, indeterminateState);
        defaultSchemeBundle.registerColorScheme(disabledScheme,
                determinateDisabledState, indeterminateDisabledState);
        defaultSchemeBundle.registerColorScheme(disabledScheme,
                ColorSchemeAssociationKind.BORDER,
                determinateDisabledState, indeterminateDisabledState);

        // for uneditable fields
        ComponentState editable = new ComponentState("editable",
                new ComponentStateFacet[] {ComponentStateFacet.ENABLE, ComponentStateFacet.EDITABLE},
                null);
        ComponentState uneditable = new ComponentState("uneditable",
                editable, new ComponentStateFacet[] {ComponentStateFacet.ENABLE},
                new ComponentStateFacet[] {ComponentStateFacet.EDITABLE});
        defaultSchemeBundle.registerColorScheme(
                defaultSchemeBundle.getColorScheme(editable),
                ColorSchemeAssociationKind.FILL, uneditable
        );

        // for text highlight
        ColorSchemes kitchenSinkSchemes = SubstanceSkin
                .getColorSchemes("org/pushingpixels/substance/api/skin/kitchen-sink.colorschemes");
        SubstanceColorScheme highlightColorScheme = kitchenSinkSchemes
                .get("Moderate Highlight");
//        SubstanceColorScheme highlightColorScheme = activeScheme.hueShift(-0.5).saturate(0.5).tint(0.5);
        defaultSchemeBundle.registerHighlightColorScheme(highlightColorScheme);

        registerDecorationAreaSchemeBundle(defaultSchemeBundle,
                DecorationAreaType.NONE);

        CopyMutableColorScheme chrome = new CopyMutableColorScheme("Cerulean Chrome", pressedScheme);
        chrome.setUltraDarkColor(chrome.getExtraLightColor());
        registerDecorationAreaSchemeBundle(new SubstanceColorSchemeBundle(pressedScheme, pressedScheme, disabledScheme), chrome,
                DecorationAreaType.PRIMARY_TITLE_PANE,
                DecorationAreaType.SECONDARY_TITLE_PANE);


        this.registerAsDecorationArea(enabledScheme,
                DecorationAreaType.PRIMARY_TITLE_PANE_INACTIVE,
                DecorationAreaType.SECONDARY_TITLE_PANE_INACTIVE);


        registerAsDecorationArea(activeScheme.saturate(-0.75),
                DecorationAreaType.HEADER, DecorationAreaType.FOOTER,
                DecorationAreaType.GENERAL);



        this.buttonShaper = new ClassicButtonShaper();
        this.fillPainter = new ClassicFillPainter();

        this.decorationPainter = new ArcDecorationPainter();

        this.highlightPainter = new GlassHighlightPainter();
        this.borderPainter = new GlassBorderPainter();
        UIManager.put(SubstanceLookAndFeel.WINDOW_AUTO_DEACTIVATE, Boolean.TRUE);
    }

    /*
      * (non-Javadoc)
      *
      * @see org.pushingpixels.substance.skin.SubstanceSkin#getDisplayName()
      */
    @Override
    public String getDisplayName() {
        return NAME;
    }
}

class CopyMutableColorScheme extends BaseColorScheme {

    Color foregroundColor;
    Color ultraLightColor;
    Color extraLightColor;
    Color lightColor;
    Color midColor;
    Color darkColor;
    Color ultraDarkColor;

    public CopyMutableColorScheme(String name, SubstanceColorScheme copy) {
        super(name, copy.isDark());
        foregroundColor = copy.getForegroundColor();
        ultraLightColor = copy.getUltraLightColor();
        extraLightColor = copy.getExtraLightColor();
        lightColor = copy.getLightColor();
        midColor = copy.getMidColor();
        darkColor = copy.getDarkColor();
        ultraDarkColor = copy.getUltraDarkColor();
    }

    public void setDark(boolean isDark) {
        this.isDark = isDark;
    }

    @Override
    public Color getDarkColor() {
        return darkColor;
    }

    public void setDarkColor(Color darkColor) {
        this.darkColor = darkColor;
    }

    @Override
    public Color getExtraLightColor() {
        return extraLightColor;
    }

    public void setExtraLightColor(Color extraLightColor) {
        this.extraLightColor = extraLightColor;
    }

    @Override
    public Color getForegroundColor() {
        return foregroundColor;
    }

    public void setForegroundColor(Color foregroundColor) {
        this.foregroundColor = foregroundColor;
    }

    @Override
    public Color getLightColor() {
        return lightColor;
    }

    public void setLightColor(Color lightColor) {
        this.lightColor = lightColor;
    }

    @Override
    public Color getMidColor() {
        return midColor;
    }

    public void setMidColor(Color midColor) {
        this.midColor = midColor;
    }

    @Override
    public Color getUltraDarkColor() {
        return ultraDarkColor;
    }

    public void setUltraDarkColor(Color ultraDarkColor) {
        this.ultraDarkColor = ultraDarkColor;
    }

    @Override
    public Color getUltraLightColor() {
        return ultraLightColor;
    }

    public void setUltraLightColor(Color ultraLightColor) {
        this.ultraLightColor = ultraLightColor;
    }
}
