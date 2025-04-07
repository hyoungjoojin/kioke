export interface Page {
  pageId: string;
  date: string;
  title: string;
  content: string;
}

export interface PagePreview {
  pageId: string;
  title: string;
  date: Date;
}
