import { WidgetType } from '@/types/dashboard';

const WidgetDimensions: Record<
  WidgetType,
  {
    w: number;
    h: number;
  }
> = {
  [WidgetType.ADD_PAGE]: { w: 1, h: 1 },
  [WidgetType.JOURNAL_COVER]: { w: 2, h: 5 },
  [WidgetType.WEATHER]: { w: 1, h: 1 },
};

const DEFAULT_DASHBOARD_LAYOUT_CONFIGURATION = {
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
};

export { WidgetType, WidgetDimensions, DEFAULT_DASHBOARD_LAYOUT_CONFIGURATION };
