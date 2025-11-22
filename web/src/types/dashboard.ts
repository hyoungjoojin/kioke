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
      type: WidgetType.ADD_PAGE;
      content: AddPageWidgetContent;
    }
  | {
      type: WidgetType.JOURNAL_COVER;
      content: JournalCoverWidgetContent;
    }
  | {
      type: WidgetType.WEATHER;
      content: WeatherWidgetContent;
    };

const enum WidgetType {
  ADD_PAGE = 'ADD_PAGE',
  JOURNAL_COVER = 'JOURNAL_COVER',
  WEATHER = 'WEATHER',
}

type AddPageWidgetContent = {
  journalId: string;
};

type JournalCoverWidgetContent = {
  journalId: string;
};

type WeatherWidgetContent = {};

export { WidgetType };
export type { Dashboard, Widget, WidgetContent };
