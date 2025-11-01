import type { WidgetType } from '@/constant/dashboard';

interface Dashboard {
  widgets: Widget[];
}

type Widget = {
  id: string;
  x: number;
  y: number;
} & WidgetContent;

type WidgetContent =
  | {
      type: WidgetType.JOURNAL_COVER;
      content: {
        journalId: string;
      };
    }
  | {
      type: WidgetType.WEATHER;
      content: {};
    };

export type { Dashboard, Widget, WidgetContent };
