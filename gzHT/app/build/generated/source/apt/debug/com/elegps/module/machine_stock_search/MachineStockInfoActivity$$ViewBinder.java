// Generated code from Butter Knife. Do not modify!
package com.elegps.module.machine_stock_search;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MachineStockInfoActivity$$ViewBinder<T extends com.elegps.module.machine_stock_search.MachineStockInfoActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2130968697, "field 'ivBack' and method 'onViewClicked'");
    target.ivBack = finder.castView(view, 2130968697, "field 'ivBack'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onViewClicked();
        }
      });
    view = finder.findRequiredView(source, 2130968809, "field 'tvMachineNO'");
    target.tvMachineNO = finder.castView(view, 2130968809, "field 'tvMachineNO'");
    view = finder.findRequiredView(source, 2130968818, "field 'tvTaskID'");
    target.tvTaskID = finder.castView(view, 2130968818, "field 'tvTaskID'");
    view = finder.findRequiredView(source, 2130968807, "field 'tvFlowNO'");
    target.tvFlowNO = finder.castView(view, 2130968807, "field 'tvFlowNO'");
    view = finder.findRequiredView(source, 2130968805, "field 'tvCreateTime'");
    target.tvCreateTime = finder.castView(view, 2130968805, "field 'tvCreateTime'");
    view = finder.findRequiredView(source, 2130968817, "field 'tvStatusText'");
    target.tvStatusText = finder.castView(view, 2130968817, "field 'tvStatusText'");
    view = finder.findRequiredView(source, 2130968808, "field 'tvMachineModel'");
    target.tvMachineModel = finder.castView(view, 2130968808, "field 'tvMachineModel'");
    view = finder.findRequiredView(source, 2130968816, "field 'tvRemark'");
    target.tvRemark = finder.castView(view, 2130968816, "field 'tvRemark'");
  }

  @Override public void unbind(T target) {
    target.ivBack = null;
    target.tvMachineNO = null;
    target.tvTaskID = null;
    target.tvFlowNO = null;
    target.tvCreateTime = null;
    target.tvStatusText = null;
    target.tvMachineModel = null;
    target.tvRemark = null;
  }
}
