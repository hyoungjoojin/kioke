import { JournalPreview } from './journal';

export interface Shelf {
  shelfId: string;
  name: string;
  journals: JournalPreview[];
  isArchive: boolean;
}
