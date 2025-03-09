import { Journal } from "../primitives/journal";

export interface CreateJournalResponseBody {
  jid: string;
  title: string;
}

export type GetJournalResponseBody = Journal;
