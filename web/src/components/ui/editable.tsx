'use client';

import { useClickOutside } from '@/hooks/useClickOutside';
import { cn } from '@/lib/utils';
import { useRef, useState } from 'react';

interface EditableDivProps {
  initialContent?: string;
  defaultContentOnEmpty?: string;
  onSubmit?: (content: string) => void;
  className?: string;
}

export function EditableDiv({
  initialContent = '',
  defaultContentOnEmpty = '',
  onSubmit,
  className,
}: EditableDivProps) {
  const [focused, setFocused] = useState(false);
  const [content, setContent] = useState(initialContent);

  const handleSubmit = () => {
    setFocused(false);

    if (onSubmit) {
      onSubmit(content);
    }
  };

  const inputRef = useRef<HTMLInputElement | null>(null);
  useClickOutside(inputRef, handleSubmit);

  return (
    <div>
      {focused ? (
        <input
          type='text'
          value={content}
          onChange={(e) => {
            setContent(e.target.value);
          }}
          onKeyDown={(e) => {
            if (e.key === 'Enter') {
              handleSubmit();
            }
          }}
          autoFocus
          ref={inputRef}
          className={cn('border-0 ring-0 focus-visible:ring-0', className)}
        />
      ) : (
        <div className={cn(className)}>
          <span
            onClick={() => {
              setFocused(true);
            }}
          >
            {content === '' ? defaultContentOnEmpty : content}
          </span>
        </div>
      )}
    </div>
  );
}
