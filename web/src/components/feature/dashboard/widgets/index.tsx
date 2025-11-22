import AddPageWidget, {
  AddPageWidgetConfigForm,
  getAddPageWidgetDefaultContent,
} from './AddPageWidget';
import JournalCoverWidget from './JournalCover/JournalCoverWidget';
import JournalCoverWidgetForm from './JournalCover/JournalCoverWidgetForm';
import WeatherWidget from './Weather/WeatherWidget';
import { WidgetType } from '@/constant/dashboard';
import type { Widget, WidgetContent } from '@/types/dashboard';
import type {
  ComponentType,
  ForwardRefExoticComponent,
  PropsWithoutRef,
} from 'react';

type WidgetProps = {
  widget: Widget;
  isPreview: boolean;
};

interface WidgetFormRef {
  submit: () => void;
}

interface WidgetFormProps {
  content: WidgetContent;
  onSubmit: (content: WidgetContent) => void;
}

const Widgets: {
  [W in WidgetType]: {
    main: ComponentType<WidgetProps>;
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
  [WidgetType.ADD_PAGE]: {
    main: AddPageWidget,
    defaultContent: getAddPageWidgetDefaultContent,
    configurable: true,
    form: AddPageWidgetConfigForm,
  },
  [WidgetType.JOURNAL_COVER]: {
    main: JournalCoverWidget,
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
