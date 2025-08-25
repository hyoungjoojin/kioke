import type { DashboardWidgetType } from '@/constant/dashboard';

export interface Dashboard {
  widgets: Widget[];
}

export interface Widget {
  type: DashboardWidgetType;
  content: any;
  id: string;
  x: number;
  y: number;
}
