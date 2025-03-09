import { JournalPreview } from "./journal";

export interface Shelf {
  id: string;
  name: string;
  journals: JournalPreview[];
  isArchive: boolean;
}
