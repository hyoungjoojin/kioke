"use client";

import { TransactionsManager } from "@/utils/transactions";
import { EditorContent, EditorEvents, useEditor } from "@tiptap/react";
import StarterKit from "@tiptap/starter-kit";
import { debounce } from "lodash";
import { useEffect } from "react";

interface PageEditorProps {
  journalId: string;
  pageId: string;
  content?: string;
}

export default function PageEditor({
  journalId,
  pageId,
  content = "",
}: PageEditorProps) {
  const editorInstance = useEditor({
    extensions: [StarterKit],
    content,
    immediatelyRender: false,
    shouldRerenderOnTransaction: false,
    onUpdate: debounce(({ transaction }: EditorEvents["update"]) => {
      TransactionsManager.getTransactionsManager().addTransaction(
        journalId,
        pageId,
        transaction,
      );
    }, 100),
  });

  useEffect(() => {
    if (editorInstance) {
      editorInstance.commands.setContent(content);
    }
  });

  if (!editorInstance) {
    return null;
  }

  return <EditorContent editor={editorInstance} />;
}
