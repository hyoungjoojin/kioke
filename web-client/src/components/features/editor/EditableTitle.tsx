import { EditorContent, useEditor } from "@tiptap/react";
import Document from "@tiptap/extension-document";
import Text from "@tiptap/extension-text";
import { useEffect, useState } from "react";
import { cn } from "@/lib/utils";

export default function EditableTitle({
  content,
  onUpdate = () => {},
  className = "",
}: {
  content: string;
  onUpdate?: (text: string) => void;
  className?: string;
}) {
  const [editable, setEditable] = useState(false);

  const editor = useEditor({
    extensions: [
      Document.extend({
        content: "text*",
        onBlur(this) {
          this.editor.setOptions({ editable: false });
          onUpdate(this.editor.getText());
          setEditable(false);
          return true;
        },
        addKeyboardShortcuts() {
          return {
            Enter: ({ editor }) => {
              editor.setOptions({ editable: false });
              onUpdate(editor.getText());
              setEditable(false);
              return true;
            },
          };
        },
      }),
      Text,
    ],
    content: `<h1>${content}</h1>`,
    immediatelyRender: false,
    parseOptions: {
      preserveWhitespace: false,
    },
  });

  useEffect(() => {
    if (editor) {
      editor.commands.setContent(`<h1>${content}</h1>`);
    }
  }, [content]);

  return editor ? (
    <div
      className={cn(
        "text-3xl p-2 rounded-xl",
        "hover:border",
        editable ? "border border-black" : "hover:border-gray-300",
        className,
      )}
      onClick={() => {
        setEditable(true);
        editor.setOptions({ editable: true });
      }}
    >
      <EditorContent editor={editor} />
    </div>
  ) : null;
}
