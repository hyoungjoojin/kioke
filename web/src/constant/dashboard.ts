export const enum DashboardWidgetType {
  JOURNAL_LIST = 'JOURNAL_LIST',
}

export const DashboardWidgetDimensions: Record<
  DashboardWidgetType,
  {
    w: number;
    h: number;
  }
> = {
  [DashboardWidgetType.JOURNAL_LIST]: { w: 2, h: 5 },
};

export const DEFAULT_DASHBOARD_LAYOUT_CONFIGURATION = {
  columns: {
    xl: 12,
    lg: 10,
    md: 6,
    sm: 4,
    xs: 2,
  },
  breakpoints: {
    xl: 1200,
    lg: 996,
    md: 768,
    sm: 480,
    xs: 0,
  },
  margin: [5, 5] as [number, number],
  rowHeight: 75,
};
