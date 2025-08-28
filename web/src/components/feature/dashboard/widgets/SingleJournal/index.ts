import SingleJournalWidgetEditModal from './edit';
import SingleJournalWidgetPreview from './preview';
import SingleJournalWidget from './widget';

export interface SingleJournalWidgetContent {
  journalId?: string;
}

const SingleJournalWidgetDefaultContent =
  async (): Promise<SingleJournalWidgetContent> => {
    return {
      journalId: '',
    };
  };

export {
  SingleJournalWidget,
  SingleJournalWidgetPreview,
  SingleJournalWidgetEditModal,
  SingleJournalWidgetDefaultContent,
};
