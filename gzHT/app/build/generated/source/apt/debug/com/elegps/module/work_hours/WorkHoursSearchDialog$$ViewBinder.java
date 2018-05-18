// Generated code from Butter Knife. Do not modify!
package com.elegps.module.work_hours;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class WorkHoursSearchDialog$$ViewBinder<T extends com.elegps.module.work_hours.WorkHoursSearchDialog> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2130968783, "field 'startData'");
    target.startData = finder.castView(view, 2130968783, "field 'startData'");
    view = finder.findRequiredView(source, 2130968622, "field 'endData'");
    target.endData = finder.castView(view, 2130968622, "field 'endData'");
    view = finder.findRequiredView(source, 2130968780, "field 'search' and method 'onViewClicked'");
    target.search = finder.castView(view, 2130968780, "field 'search'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onViewClicked();
        }
      });
  }

  @Override public void unbind(T target) {
    target.startData = null;
    target.endData = null;
    target.search = null;
  }
}
