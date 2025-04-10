import { Input } from "@/components/ui/input";
import { cn } from "@/lib/utils";
import { useState } from "react";

interface EditableTitleProps {
  content: string;
  onSubmit: (content: string) => void;
}

export default function EditableTitle({
  content,
  onSubmit,
}: EditableTitleProps) {
  const [focused, setFocused] = useState(false);
  const [modifiedContent, setModifiedContent] = useState(content);

  return (
    <div
      onClick={() => {
        setFocused(true);
      }}
    >
      {focused ? (
        <Input
          value={modifiedContent}
          onChange={(e) => {
            setModifiedContent(e.target.value);
          }}
          onKeyDown={(e) => {
            if (e.key === "Enter") {
              setFocused(false);
              onSubmit(modifiedContent);
            }
          }}
          autoFocus
          className="h-full md:text-3xl"
        />
      ) : (
        <h1
          className={cn(
            "hover:border hover:border-muted text-3xl",
            content === "" ? "text-gray-700" : "",
          )}
        >
          {content === "" ? "Untitled" : content}
        </h1>
      )}
    </div>
  );
}
