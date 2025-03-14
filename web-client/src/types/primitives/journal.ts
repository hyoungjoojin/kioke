import { Page } from "./page";

export interface Journal {
  id: string;
  title: string;
  description: string;
  createdAt: string;
  lastModified: string;
  pages: PagePreview[];
}

export interface JournalPreview {
  id: string;
  title: string;
  createdAt: string;
}
