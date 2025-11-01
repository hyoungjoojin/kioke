import JournalCoverWidget from './JournalCover/JournalCoverWidget';
import JournalCoverWidgetForm from './JournalCover/JournalCoverWidgetForm';
import JournalCoverWidgetPreview from './JournalCover/JournalCoverWidgetPreview';
import WeatherWidget from './Weather/WeatherWidget';
import WeatherWidgetPreview from './Weather/WeatherWidgetPreview';
import { WidgetType } from '@/constant/dashboard';
import type { WidgetContent } from '@/types/dashboard';
import type {
  ComponentType,
  ForwardRefExoticComponent,
  PropsWithoutRef,
} from 'react';

interface WidgetProps {
  id: string;
  widget: WidgetContent;
  isEditing: boolean;
  disabled?: boolean;
}

interface WidgetFormRef {
  submit: () => void;
}

interface WidgetFormProps {
  widget: WidgetContent;
  onSubmit: (content: any) => void;
}

const Widgets: {
  [W in WidgetType]: {
    main: ComponentType<WidgetProps>;
    preview: ComponentType<any>;
    defaultContent: () => Promise<WidgetContent>;
  } & (
    | {
        configurable: true;
        form: ForwardRefExoticComponent<
          React.RefAttributes<WidgetFormRef> & PropsWithoutRef<WidgetFormProps>
        >;
      }
    | {
        configurable: false;
      }
  );
} = {
  [WidgetType.JOURNAL_COVER]: {
    main: JournalCoverWidget,
    preview: JournalCoverWidgetPreview,
    defaultContent: async () => {
      return {
        type: WidgetType.JOURNAL_COVER,
        content: {
          journalId: '',
        },
      };
    },
    configurable: true,
    form: JournalCoverWidgetForm,
  },
  [WidgetType.WEATHER]: {
    main: WeatherWidget,
    preview: WeatherWidgetPreview,
    defaultContent: async () => {
      return {
        type: WidgetType.WEATHER,
        content: {},
      };
    },
    configurable: false,
  },
};

export default Widgets;
export type { WidgetProps, WidgetFormRef, WidgetFormProps };
