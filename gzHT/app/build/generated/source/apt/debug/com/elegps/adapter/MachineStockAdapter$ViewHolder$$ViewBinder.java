// Generated code from Butter Knife. Do not modify!
package com.elegps.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MachineStockAdapter$ViewHolder$$ViewBinder<T extends com.elegps.adapter.MachineStockAdapter.ViewHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2130968791, "field 'textView1'");
    target.textView1 = finder.castView(view, 2130968791, "field 'textView1'");
    view = finder.findRequiredView(source, 2130968792, "field 'textView2'");
    target.textView2 = finder.castView(view, 2130968792, "field 'textView2'");
    view = finder.findRequiredView(source, 2130968793, "field 'textView3'");
    target.textView3 = finder.castView(view, 2130968793, "field 'textView3'");
    view = finder.findRequiredView(source, 2130968794, "field 'textView4'");
    target.textView4 = finder.castView(view, 2130968794, "field 'textView4'");
  }

  @Override public void unbind(T target) {
    target.textView1 = null;
    target.textView2 = null;
    target.textView3 = null;
    target.textView4 = null;
  }
}
