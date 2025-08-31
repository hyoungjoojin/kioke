import {
  JournalCollectionWidget,
  JournalCollectionWidgetDefaultContent,
  JournalCollectionWidgetEditModal,
  JournalCollectionWidgetPreview,
} from './JournalCollection';
import {
  SingleJournalWidget,
  SingleJournalWidgetDefaultContent,
  SingleJournalWidgetEditModal,
  SingleJournalWidgetPreview,
} from './SingleJournal';
import { DashboardWidgetType } from '@/constant/dashboard';
import type { ComponentType } from 'react';

export interface DashboardWidgetEditModalProps {
  onSubmit: (content: any) => void;
}

const Widgets: {
  [K in DashboardWidgetType]: {
    main: ComponentType<any>;
    preview: ComponentType<any>;
    edit: ComponentType<
      DashboardWidgetEditModalProps & {
        content: any;
      }
    >;
    default: () => Promise<any>;
  };
} = {
  [DashboardWidgetType.JOURNAL_COLLECTION]: {
    main: JournalCollectionWidget,
    preview: JournalCollectionWidgetPreview,
    edit: JournalCollectionWidgetEditModal,
    default: JournalCollectionWidgetDefaultContent,
  },
  [DashboardWidgetType.SINGLE_JOURNAL]: {
    main: SingleJournalWidget,
    preview: SingleJournalWidgetPreview,
    edit: SingleJournalWidgetEditModal,
    default: SingleJournalWidgetDefaultContent,
  },
};

export default Widgets;
