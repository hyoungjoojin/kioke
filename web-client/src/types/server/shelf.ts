import { JournalPreview } from "../primitives/journal";

export type GetShelfResponseBody = {
  shelfId: string;
  name: string;
  journals: JournalPreview[];
  isArchive: boolean;
};
