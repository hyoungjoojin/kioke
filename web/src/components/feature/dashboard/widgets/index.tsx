import {
  JournalList,
  JournalListEdit,
  JournalListInitial,
  JournalListPreview,
} from './JournalList';
import { DashboardWidgetType } from '@/constant/dashboard';
import type { ComponentType } from 'react';

const Widgets: {
  [K in DashboardWidgetType]: {
    main: ComponentType<any>;
    preview: ComponentType<any>;
    edit: ComponentType<any>;
    initial: () => Promise<any>;
  };
} = {
  [DashboardWidgetType.JOURNAL_LIST]: {
    main: JournalList,
    preview: JournalListPreview,
    edit: JournalListEdit,
    initial: JournalListInitial,
  },
};

export default Widgets;
