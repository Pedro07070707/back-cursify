USE bd_cursify;
GO

IF COL_LENGTH('dbo.Material', 'link_titulo') IS NULL
    ALTER TABLE dbo.Material ADD link_titulo VARCHAR(150) NULL;
GO

/* Todos os links passam a ser armazenados exclusivamente em Material_links. */
IF OBJECT_ID('dbo.Material_links', 'U') IS NULL
BEGIN
    CREATE TABLE dbo.Material_links
    (
        material_id INT NOT NULL,
        ordem INT NOT NULL,
        titulo VARCHAR(150) NOT NULL,
        url VARCHAR(500) NOT NULL,
        CONSTRAINT FK_Material_links_Material FOREIGN KEY (material_id)
            REFERENCES dbo.Material(id) ON DELETE CASCADE
    );
END;
ELSE
BEGIN
    IF COL_LENGTH('dbo.Material_links', 'titulo') IS NULL
        ALTER TABLE dbo.Material_links ADD titulo VARCHAR(150) NULL;
    IF COL_LENGTH('dbo.Material_links', 'url') IS NULL
        ALTER TABLE dbo.Material_links ADD url VARCHAR(500) NULL;
    IF COL_LENGTH('dbo.Material_links', 'ordem') IS NULL
        ALTER TABLE dbo.Material_links ADD ordem INT NULL;
    IF COL_LENGTH('dbo.Material_links', 'link') IS NOT NULL
        EXEC('UPDATE dbo.Material_links SET titulo = COALESCE(NULLIF(titulo, ''''), ''Abrir link''), url = COALESCE(NULLIF(url, ''''), link)');
    ELSE
        EXEC('UPDATE dbo.Material_links SET titulo = COALESCE(NULLIF(titulo, ''''), ''Abrir link''), url = COALESCE(NULLIF(url, ''''), '''')');
    EXEC('UPDATE dbo.Material_links SET ordem = ISNULL(ordem, 0)');
    EXEC('ALTER TABLE dbo.Material_links ALTER COLUMN titulo VARCHAR(150) NOT NULL');
    EXEC('ALTER TABLE dbo.Material_links ALTER COLUMN url VARCHAR(500) NOT NULL');
    EXEC('ALTER TABLE dbo.Material_links ALTER COLUMN ordem INT NOT NULL');
END;
GO

/* Migra o link legado antes de remover as colunas antigas de Material. */
IF COL_LENGTH('dbo.Material', 'link') IS NOT NULL
BEGIN
    EXEC('INSERT INTO dbo.Material_links (material_id, ordem, titulo, url)
          SELECT m.id, 0, COALESCE(NULLIF(m.link_titulo, ''''), ''Abrir link''), m.link
          FROM dbo.Material m
          WHERE NULLIF(m.link, '''') IS NOT NULL
            AND NOT EXISTS (
                SELECT 1 FROM dbo.Material_links ml
                WHERE ml.material_id = m.id AND ml.ordem = 0
            )');
END;
GO

IF COL_LENGTH('dbo.Material', 'link_titulo') IS NOT NULL
    ALTER TABLE dbo.Material DROP COLUMN link_titulo;
IF COL_LENGTH('dbo.Material', 'link') IS NOT NULL
    ALTER TABLE dbo.Material DROP COLUMN link;
GO
